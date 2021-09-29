package ar.edu.unahur.obj2.tareas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class TareaTest : DescribeSpec({
  val jorge = Trabajador(42)
  val pepe = Trabajador(30)
  val leila = Trabajador(45)
  val julia = Trabajador(40)
  describe("Tareas") {
    describe("Tarea Simple"){
      val HojasExcel = TareaSimple(jorge, listOf(pepe,julia,leila) ,90,50)
      it("horas Determinadas para la tarea"){
        HojasExcel.horasDeterminadas().shouldBe(16)
      }
      it("Costo de la tarea"){
        HojasExcel.costo().shouldBe(4030)
      }
      it("nomina de trabajo"){
        HojasExcel.nomina().shouldContainAll(listOf(pepe,julia,leila,jorge))
      }

    }
    describe("tarea Compleja"){
      val HojasExcel = TareaSimple(jorge, listOf(pepe,julia,leila) ,90,50)
      val HojasWorld = TareaSimple(pepe, listOf(jorge,julia),70,60)
      val Documentacion = TareaDeIntegracion(leila, listOf(HojasExcel,HojasWorld))
      it("cantidad de reuniones"){
        Documentacion.cantidadDeReuniones().shouldBe(5)
      }
      it("horas determinadas del trabajo"){
        Documentacion.horasDeterminadas().shouldBe(51)
      }
      it("nomina total"){
        Documentacion.nomina().shouldContainAll(listOf(jorge,leila,julia,pepe))
      }
      it("costo"){
        Documentacion.costo().shouldBe(8610)
      }
      describe("tarea compleja conteniendo tareas complejas"){
        val Carpeta = TareaDeIntegracion(julia, listOf(Documentacion,HojasExcel))
        it("horas determinadas"){
          Carpeta.horasDeterminadas().shouldBe(75)
        }
        it("costo"){
          Carpeta.costo().shouldBe(13019)
        }
      }
    }

  }
  describe("proyecto"){
    val liliana = Trabajador(20)
    val laura = Trabajador(30)
    val tareita = TareaSimple(liliana, listOf(laura),50,60)
    val proyectito = Proyecto(listOf(tareita), LocalDate.of(2021,9,28),LocalDate.of(2021,9,30))
    proyectito.estaAtrasado().shouldBeTrue()
  }
})
