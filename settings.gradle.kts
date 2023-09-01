rootProject.name = "dnsman"

dependencyResolutionManagement {
    repositories.mavenCentral()
}

include("domain")
include("application")
include("infra-dns")
include("infra-web")
include("infra-persistence")
include("infra-persistence-database")
