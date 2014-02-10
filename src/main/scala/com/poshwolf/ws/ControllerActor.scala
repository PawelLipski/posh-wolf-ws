package com.poshwolf.ws

import com.poshwolf.core._

import java.util.HashMap
import scala.actors.Actor

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

class ControllerActor extends Actor {

  override def act() {

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
}

