### 注意事项
1. 保持参数名不被优化，需要先关闭优化选项

		-dontoptimize
		-keepparameternames
2. 对于需要CGlib进行动态代理的对象必须对public的方法进行去final(原理是生成子类重写非final修饰的方法)

		-optimizations !method/marking/final
3. 对于Json数据的参数绑定，其方法不能进行混淆(原理是通过getter/setter方法进行处理)


		# 保留JavaBean不被混淆
		-keepclassmembers class * implements java.io.Serializable {
		  static final long serialVersionUID;
		  private static final java.io.ObjectStreamField[] serialPersistentFields;
		  !static !transient <fields>;
		  private void writeObject(java.io.ObjectOutputStream);
		  private void readObject(java.io.ObjectInputStream);
		  java.lang.Object writeReplace();
		  java.lang.Object readResolve();
		
		  public <methods>;
		}
4. 对于spring管理对象的方法名称，可以考虑不进行混淆