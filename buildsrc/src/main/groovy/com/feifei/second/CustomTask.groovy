package com.feifei.second

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class CustomTask extends DefaultTask{

    @TaskAction
    void output(){
        println("param1 is ${project.pluginExt.param1}")
        println("param2 is ${project.pluginExt.param2}")
        println("param3 is ${project.pluginExt.param3}")
        println("nest param1 is ${project.pluginExt.nestExt.nestParam1}")
        println("nest param2 is ${project.pluginExt.nestExt.nestParam2}")
        println("nest param3 is ${project.pluginExt.nestExt.nestParam3}")
    }
}