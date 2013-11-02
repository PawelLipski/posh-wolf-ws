package com.poshwolf.ws

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

private case class InitTaskRequest()
private case class SetProgressRequest(id: Int, progress: Int)
private case class GetProgressRequest(id: Int)
private case class FinishTaskRequest(id: Int)

@WebService (targetNamespace="com.poshwolf.ws")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL) 
class PoshWolfWebService {

  @WebMethod
  def initTask: Int = {
    val myId = (controller !? InitTaskRequest()).asInstanceOf[Int]

    actor {
      //controller ! InitTaskRequest(myId)

      /*listener = new ProgressListener {
        def onIterationDone(int iterNo, int partialResult) {
          controller ! (NOTE_PROGRESS, myNumber, iterNo, partialResult)
        }
      }
      // run magicAlgo(listener)*/

      for (i <- List.range(1, 30)) {
        Thread.sleep(1000)
        //println("Id: " + myId + ", progress: " + i)
        controller ! SetProgressRequest(myId, i)
      }
        
      controller ! FinishTaskRequest(myId)

    }.start()

    return myId
  }

  @WebMethod
  def getProgress( @WebParam(name="taskId") taskId: Int): Int = {
    //Array[Int] res = new Int[tn.length] ??
    //for (i <- List.range(0,tn.l)) {
    //   res(i) = controller !? getProgressRequest(tn(i))
    //} // albo jakiś ładne list comprehension
    //println("Sending request for the progress of task #" + taskId)
    val res = (controller !? GetProgressRequest(taskId)).asInstanceOf[Int]
    //println("Received progress = " + res)
    return res
  }

  private val controller = actor {
    val status = new HashMap[Int, Int]
    loop {
      receive {
        case InitTaskRequest() => 
          val newId = status.size + 1
          status.put(newId, 0)
          reply(newId)

        case SetProgressRequest(id, progress) =>  /// TODO partialResult? iterNo czy percent??
          status.put(id, progress)

        case GetProgressRequest(id) => 
          reply(status.get(id))

        case FinishTaskRequest(id) => 
          status.put(id, 100) // TODO 100 - czy to jest ok wartosc?
      }
    }
  }

  controller.start()
}

object Core {

  def main(args: Array[String]) {
    /*println("Starting controller...")
    controller.start()
    println("Controller started")

    val id = Core.initTask
    println("Started task " + id)
    val id2 = Core.initTask
    println("Started task " + id2)

    for (i <- range(1, 40)) {
      Thread.sleep(1000)
      val progress = Core.getProgress(id)
      println("Progress of task " + id + " is " + progress)
      val progress2 = Core.getProgress(id2)
      println("Progress of task " + id2 + " is " + progress2)
    }
  }*/

    val port = Properties.envOrElse("PORT", "8080")
    val url = "http://0.0.0.0:" + port + "/posh-wolf-ws";
    val endpoint = Endpoint.publish(url, new PoshWolfWebService())

    println("Waiting for requests on " + url + "...")
  }
}

