rootProject.name = "dnsman"

dependencyResolutionManagement {
    repositories.mavenCentral()
}

include("domain")
include("application")
include("infra-web")
include("infra-persistence")
include("infra-dns")
