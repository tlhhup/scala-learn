1. akka中提供`Death Watch`特性允许一个actor去watch和notified其他的actor
	1. 如果被watch的actor停止了，watcher将收到一个`Terminated(actorRef)`

			case trackMsg@RequestTrackDevice(`groupId`, _) => {
		      deviceIdToActor.get(trackMsg.deviceId) match {
		
		        case None => {
		
		          // 开启监视
		          context.watch(deviceActor)
		        }
		      }
		    }
		
		    case Terminated(deviceActor) => {
		
		    }
	2. 通过unwatch来取消监听
2. actor中的**receive方法**对message的处理

		// 实质是Receive在进行处理-->本质上就是一个偏函数
 		//it is not receive that handles the messages, it returns a Receive function that will actually handle the messages 