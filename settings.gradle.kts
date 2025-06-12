pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        jcenter()
        google()
        mavenCentral()
//       maven { url 'https://maven.aliyun.com/repository/public' } //mavenCentral()的镜像
//maven { url 'https://maven.aliyun.com/repository/google/' } //google()的镜像
//maven { url 'https://maven.aliyun.com/repository/jcenter' } //jcenter()的镜像
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
}

rootProject.name = "login"
include(":app")
 