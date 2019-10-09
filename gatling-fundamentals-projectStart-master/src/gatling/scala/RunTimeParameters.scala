import io.gatling.core.scenario.Simulation


class RunTimeParameters extends Simulation{

   def getProperty (propertyName: String, defaultValue : String): String ={
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

}
