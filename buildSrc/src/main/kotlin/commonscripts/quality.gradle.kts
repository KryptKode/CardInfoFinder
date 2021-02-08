package commonscripts

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin


apply<DetektPlugin>()
apply<KtlintPlugin>()

configure<DetektExtension> {
    input = project.files("src/main/kotlin")
    config = files("$rootDir/.detekt/config.yml")
    reports {
        xml {
            enabled = true
            destination = project.file("build/reports/detekt/report.xml")
        }
        html {
            enabled = true
            destination = project.file("build/reports/detekt/report.html")
        }
    }
}

configure<KtlintExtension>{
    android.set(true)
}
