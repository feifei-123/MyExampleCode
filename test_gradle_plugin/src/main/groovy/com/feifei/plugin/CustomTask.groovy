package  com.feifei.plugin

import org.gradle.api.tasks.TaskAction

public class CustomTask{

    @TaskAction
    void output(){
        println("param1 is ${project.pluginExt.param1}")
        println("param2 is ${project.pluginExt.param2}")
        println("param3 is ${project.pluginExt.param3}")
        println("nest param1 is ${project.pluginExt.nestExt.param1}")
        println("nest param2 is ${project.pluginExt.nestExt.param2}")
        println("nest param3 is ${project.pluginExt.nestExt.param3}")
    }
}