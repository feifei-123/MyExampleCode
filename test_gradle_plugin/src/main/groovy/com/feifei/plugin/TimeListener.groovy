package  com.feifei.plugin
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock

class TimeListener implements TaskExecutionListener, BuildListener{

    private long stamp = 0;
    private times = []

    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void buildFinished(BuildResult buildResult) {
        println ("Task spend time:")
        for(time in times){
            if(time[0] > 0){
                println("feifei test ${time[0]}ms -- ${time[1]}")
            }
        }
    }

    @Override
    void beforeExecute(Task task) {
        stamp = System.currentTimeMillis()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        def ms = System.currentTimeMillis() - stamp
        times.add([ms,task.path])
        task.project.logger.warn("feifei test ${task.path} speed ${ms} ms")
    }
}