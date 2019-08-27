# 忽略警告
-ignorewarnings
#打印处理信息，失败时会打印堆栈信息
-verbose
# 不对input进行压缩
-dontshrink

# 保持目录结构
-keepdirectories
#不能混淆泛型、抛出的异常、注解默认值、原始行号等
-keepattributes Signature, Exceptions, *Annotation*, InnerClasses, Deprecated, EnclosingMethod
# 对于包名不进行混淆
-keeppackagenames org.tlh.springboot.**

# 保留使用RestController、Controller、Component、Service、Repository注解的类不被混淆
-keep,allowoptimization @org.springframework.web.bind.annotation.RestController class *
-keep,allowoptimization @org.springframework.stereotype.** class *

# 保留Entity注解的类不混淆
-keep,allowoptimization @javax.persistence.Entity class *

# 保留jackson的注解的属性不混淆
-keepclassmembernames class * {
    @com.fasterxml.jackson.annotation.** <fields>;
}

# 保留public修饰的类中的public、protected方法不被混淆
#-keepclassmembernames public class * {
#  public protected <methods>;
#}

# 保留接口不混淆
-keep public interface *

# 保留注解不被混淆
-keep public @interface * {
  ** default (*);
}
# 主类不混淆
-keep class org.tlh.springboot.ProGuardApplication {
    public static void main(java.lang.String[]);
}

# 保留数据库驱动
-keep class * implements java.sql.Driver

# 保留枚举类不被混淆
-keepclassmembers,allowoptimization enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

# 保持依赖注入不被混淆
-keepclassmembers class * {
  @org.springframework.beans.factory.annotation.Autowired *;
  @javax.annotation.Resource *;
}

# 保持RMI调用不被混淆
-keep interface * extends java.rmi.Remote {
    <methods>;
}
-keep class * implements java.rmi.Remote {
  <init>(java.rmi.activation.ActivationID, java.rmi.MarshalledObject);
}

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

# 避免类名被标记为final
-optimizations !class/marking/final
# 避免方法被标记为final
-optimizations !method/marking/final