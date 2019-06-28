1. 模式匹配
	1. 变量匹配

			val v42 = 42
			Some(3) match {
			  case Some(`v42`) => println("42") //不用反引号，理解为变量，一直输出42  引用前面定义的变量
			  case _ => println("Not 42") //输出
			}
	2. 变量绑定匹配

			val person = Person("Tony",18)
			person match {
				// 使用 @ 将匹配的对象绑定到变量名上
		        case p @ Person(_,age) => println(s"${p.name},age is $age")
		        case _ => println("Not a person")
		    }