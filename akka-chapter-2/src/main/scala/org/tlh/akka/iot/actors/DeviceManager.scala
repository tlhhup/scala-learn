package org.tlh.akka.iot.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}

/**
  * 负责管理deviceGroup
  * 1. 如果deviceGroup存在，则转发给该deviceGroup
  * 2. 如果deviceGroup不存在，则进行创建
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class DeviceManager extends Actor with ActorLogging {

  var groupIdToActor = Map.empty[String, ActorRef]
  var actorToGroupId = Map.empty[ActorRef, String]

  import org.tlh.akka.iot.model._

  override def receive: Receive = {
    case trackMsg@RequestTrackDevice(groupId, _) => {
      groupIdToActor.get(groupId) match {
        case Some(groupActor) => groupActor.forward(trackMsg)

        case None => {
          log.info("Creating device group actor for {}", groupId)
          val groupActor = context.actorOf(DeviceGroup.props(groupId), s"group-$groupId")
          groupIdToActor += groupId -> groupActor
          actorToGroupId += groupActor -> groupId

          groupActor.forward(trackMsg)

          context.watch(groupActor)
        }
      }
    }

    case RequestGroupTemperatures(requestId, groupId) => {
      groupIdToActor.get(groupId) match {
        case Some(groupActor) => groupActor.forward(RequestAllTemperatures(requestId))
        case None => log.warning("No group:{} found", groupId)
      }
    }

    case Terminated(actorRef) => {
      val groupId = actorToGroupId(actorRef)
      log.info("Device group actor for {} has been terminated", groupId)
      groupIdToActor -= groupId
      actorToGroupId -= actorRef
    }
  }

}

object DeviceManager {

  def props: Props = Props[DeviceManager]

}