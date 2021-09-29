package ar.edu.unahur.obj2.tareas
import java.time.LocalDate

abstract class Tarea(val responsable : Trabajador){
    abstract fun costo() : Int
    abstract fun nomina() : List<Trabajador>
    abstract fun horasDeterminadas() : Int

}

class TareaSimple(responsable: Trabajador,val equipo : List<Trabajador> ,  val costoInfraestructura : Int, val horasEstimadas : Int) : Tarea(responsable){
    override fun horasDeterminadas() : Int{
        return (horasEstimadas / equipo.size).toInt()
    }
    override fun costo() : Int{
        return costoInfraestructura + equipo.sumBy{it.SueldoPorHora} * this.horasDeterminadas() + responsable.SueldoPorHora * horasEstimadas
    }

    override fun nomina(): List<Trabajador> {
        return equipo + responsable
    }

}

class TareaDeIntegracion(responsable: Trabajador, val tareasAcoordinar : List<Tarea>) : Tarea(responsable){

    fun cantidadDeReuniones() : Int{
        return (tareasAcoordinar.sumBy {it.horasDeterminadas()} / 8).toInt()
    }
    override fun horasDeterminadas(): Int {
        return tareasAcoordinar.sumBy {x -> x.horasDeterminadas() } + cantidadDeReuniones()
    }

    override fun nomina(): List<Trabajador> {
        val nominasuplente = mutableListOf<Trabajador>()
        tareasAcoordinar.forEach{x -> nominasuplente.addAll(x.nomina())}
        return (nominasuplente.toSet() + responsable).toList()
    }

    override fun costo(): Int {
        return tareasAcoordinar.sumBy { it.costo()} + ((tareasAcoordinar.sumBy { it.costo()} *3)/ 100).toInt()
    }

}

class Trabajador(val SueldoPorHora: Int) {
}


class Proyecto(val tareasRequeridas : List<Tarea>, val fechaDeInicio : LocalDate,val fechaDeFinalizacion : LocalDate){

    fun diasRequeridos(): Int{
        return (tareasRequeridas.sumBy { it.horasDeterminadas() } / 8).toInt()
    }
    fun estaAtrasado(): Boolean{
        return fechaDeInicio.plusDays(diasRequeridos().toLong())  > fechaDeFinalizacion.plusDays(1)
    }
}
