package com.poshwolf.ws

import com.poshwolf.core._

import java.net.InetSocketAddress
import java.util.HashMap
import javax.jws.WebService
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebParam.Mode
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.Use
import javax.jws.soap.SOAPBinding.Style
import javax.xml.ws.Endpoint
import List.range
import util.Properties
import scala.actors.Actor._

private case class PostTaskRequest(task: TaskDefinition)
private case class GetTaskDefinitionRequest(id: Int)
private case class SetProgressRequest(id: Int, progress: Int)
private case class GetProgressRequest(id: Int)
private case class GetResultRequest(id: Int)
private case class GetAllProgressesRequest(ids: Array[Int])
private case class FinishTaskRequest(id: Int, result: Result)

class TaskProgressEntry(_id: Int, _progress: Int) {

  private var id = _id
  private var progress = _progress

  def this() = this(0, 0)

  def getId = id
  def setId(_id: Int) = { id = _id }

  def getProgress = progress
  def setProgress(_progress: Int) = { progress = _progress }
}

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
    
    println(config.getMaxIterations)
    println(config.getNestNumber)
    println(config.getNestsToAbandonNumber)
    println(config.getDiscoveryProbability)
    println

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
  
  @WebMethod
  def intMatrixTest(
    @WebParam(name="matrix")
  matrix : Array[Array[Int]]) = "Hi " + matrix(1)(1)

  private val controller = actor {
    val tasks = new HashMap[Int, TaskDefinition]
    val status = new HashMap[Int, Int]
    val results = new HashMap[Int, Result]

    loop {
      receive {
        case PostTaskRequest(task) => 
          val newId = status.size + 1
          tasks.put(newId, task)
          status.put(newId, 0)
          reply(newId)

        case GetTaskDefinitionRequest(id) => 
          reply(tasks.get(id))

        case SetProgressRequest(id, progress) =>  
          status.put(id, progress)

        case GetProgressRequest(id) => 
          reply(status.get(id))

        case GetResultRequest(id) =>  
          reply(results.get(id))

        case GetAllProgressesRequest(ids) => 
          val res = for(id <- ids) yield new TaskProgressEntry(id, status.get(id))
          reply(res.toArray)

        case FinishTaskRequest(id, result) => 
          status.put(id, 100) 
          results.put(id, result)
      }
    }
  }

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


