package com.poshwolf.ws

import com.poshwolf.core._

import java.net.InetSocketAddress
import javax.jws.WebService
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebParam.Mode
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.Use
import javax.jws.soap.SOAPBinding.Style
import javax.xml.ws.Endpoint
import List.range
import scala.actors.Actor._
import util.Properties


@WebService (targetNamespace="com.poshwolf.ws")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL) 
class PoshWolfWebService {

  @WebMethod
  def postTask( 
    @WebParam(name = "jobCount") jobCount: Int,
    @WebParam(name = "machineCount") machineCount: Int,
    @WebParam(name = "opDurationsForJobs") opDurationsForJobs: Array[Array[Int]],
    @WebParam(name = "config") config: CuckooSolverConfig
  ): Int = {    
    
    val task = new TaskDefinition(jobCount, machineCount, opDurationsForJobs)
    val myId = (controller !? PostTaskRequest(task)).asInstanceOf[Int]

    actor {

      var listener = new ProgressListener {
        override def onProgress(progress: Int, partialResult: Int) {
          controller ! SetProgressRequest(myId, progress)
        }
      }      
      val solver = new CuckooSolver(config)

      val result = solver.solve(task, listener)

      controller ! FinishTaskRequest(myId, result)

    }.start()

    return myId
  }

  @WebMethod
  def getProgress( @WebParam(name="taskId") taskId: Int): Int = 
    (controller !? GetProgressRequest(taskId)).asInstanceOf[Int]

  @WebMethod
  def getAllProgresses( @WebParam(name="taskIds") taskIds: Array[Int]): Array[TaskProgressEntry] = 
    (controller !? GetAllProgressesRequest(taskIds)).asInstanceOf[Array[TaskProgressEntry]]

  @WebMethod
  def getResult( @WebParam(name="taskId") taskId: Int): Result = 
    (controller !? GetResultRequest(taskId)).asInstanceOf[Result]

  @WebMethod
  def getResultAndInput( @WebParam(name="taskId") taskId: Int): ResultAndInput = 
    new ResultAndInput(
     (controller !? GetTaskDefinitionRequest(taskId)).asInstanceOf[TaskDefinition],
     (controller !? GetResultRequest(taskId)).asInstanceOf[Result])

  @WebMethod
  def solve( @WebParam(name="task") task: TaskDefinition): Result = {
    val result = new Result()

    val order = for (i <- List.range(0, task.getMachineCount)) yield (i + 1)

    result.setJobOrder(order.toArray)
    result.setExecutionTimespan(2345)
    result.setComputationTime(23.45)

    result
  }

  private val controller = new ControllerActor
  controller.start()
}

object WebServiceMain {

  def main(args: Array[String]) {

    val port = Properties.envOrElse("PORT", "8080")
    val url = "http://0.0.0.0:" + port + "/" 
    val endpoint = Endpoint.publish(url, new PoshWolfWebService())

    println("Waiting for requests on " + url + "...")
  }
}


