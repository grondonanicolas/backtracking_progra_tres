package gui;

import java.util.*;
import java.time.DayOfWeek;


public class Main {

    /*
     * Andrea esta organizando la cursada del próximo cuatrimestre, su objetivo es cursar los 5 días de la semana y
     * debe organizar en qué materia inscribirse cada día. Como información tiene las materias que quiere cursar y
     * los días de la semana en que se dictan (no todas las materias se dictan todos los días). El objetivo es diseñar
     * un algoritmo que le indique las distintas combinaciones de inscripción que tienen con el objetivo de cursar
     * una materia cada día.
     * Por ejemplo, lunes (M1, M3, M5), martes (M2, M1), miércoles(M3,M4,M5), jueves(M2,M4) y viernes(M3, M5)
     * Posibles combinaciones son: L=M1, M=M2, Mier=M5, J=M4 y V=M3; otra sería L=M3, M=M1, Mier=M4, J=M2y V=M5, etc.
     * */

    public static void main(String[] args) {
        obteneMateriasParaLaSemanaConXMaterias(15);
        obteneMateriasParaLaSemanaConXMaterias(30);
        obteneMateriasParaLaSemanaConXMaterias(45);
        obteneMateriasParaLaSemanaConXMaterias(60);
        obteneMateriasParaLaSemanaConXMaterias(75);
        obteneMateriasParaLaSemanaConXMaterias(90);
        obteneMateriasParaLaSemanaConXMaterias(105);
        obteneMateriasParaLaSemanaConXMaterias(120);
    }

    public static List<Materia[]> obteneMateriasParaLaSemanaConXMaterias(int cantidadMaterias) {
        Map<DayOfWeek, List<Materia>> materiasPorDia = new HashMap <DayOfWeek,List<Materia>>();
        List<Materia> materias = new ArrayList<Materia>();

        for(int i = 0; i < cantidadMaterias; i ++  ){
            String materiaNombre = "M" + 1;
            Materia materia = new Materia(materiaNombre);
            materias.add(materia);
        }
        List<Materia> materiasLunes = new ArrayList<Materia>();
        int materiasLunesDisponibles = cantidadMaterias/ 5;
        for(int i  = 0 ; i < materiasLunesDisponibles; i ++){
            materiasLunes.add(materias.get(i));
        }

        List<Materia> materiasMartes = new ArrayList<Materia>();
        int materiasMartesDisponibles = (cantidadMaterias/ 5) * 2;
        for(int i  = 0 ; i < materiasMartesDisponibles; i ++){
            materiasMartes.add(materias.get(i));
        }

        List<Materia> materiasMiercoles = new ArrayList<Materia>();
        int materiasMiercolesDisponibles = (cantidadMaterias/ 5) * 3;
        for(int i  = materiasMartesDisponibles ; i < materiasMiercolesDisponibles; i ++){
            materiasMiercoles.add(materias.get(i));
        }

        List<Materia> materiasJueves = new ArrayList<Materia>();
        int materiasJuevesDisponibles = (cantidadMaterias/ 5) * 4;
        for(int i  = materiasMartesDisponibles ; i < materiasJuevesDisponibles; i ++){
            materiasJueves.add(materias.get(i));
        }


        List<Materia> materiasViernes = new ArrayList<Materia>();
        int materiasViernesDisponibles = (cantidadMaterias/ 5) * 5;
        for(int i  = materiasJuevesDisponibles ; i < materiasViernesDisponibles; i ++){
            materiasViernes.add(materias.get(i));
        }

        materiasPorDia.put(DayOfWeek.MONDAY, materiasLunes);
        materiasPorDia.put(DayOfWeek.TUESDAY, materiasMartes);
        materiasPorDia.put(DayOfWeek.WEDNESDAY, materiasMiercoles);
        materiasPorDia.put(DayOfWeek.THURSDAY, materiasJueves);
        materiasPorDia.put(DayOfWeek.FRIDAY, materiasViernes);
        long startTime = System.currentTimeMillis();
        List<Materia[]>  soluciones = obteneMateriasParaLaSemana(materiasPorDia);
        long endTime = System.currentTimeMillis();
        System.out.println("Cantidad de materias con poda: "+ cantidadMaterias + "\nSoluciones " + soluciones.size() + "\nDuracion: ");
        System.out.println(startTime);
        System.out.println(endTime);


        long startTimeWithoutPoda = System.currentTimeMillis();
        List<Materia[]>  solucionesSinPoda = obteneMateriasParaLaSemanaSinPoda(materiasPorDia);
        long endTimeWithoutPoda = System.currentTimeMillis();
        System.out.println("Cantidad de materias sin poda: "+ cantidadMaterias + "\nSoluciones " + solucionesSinPoda.size() + "\nDuracion: ");
        System.out.println(startTimeWithoutPoda);
        System.out.println(endTimeWithoutPoda);
        return soluciones;
    }

    public static List<Materia[]> obteneMateriasParaLaSemana(Map<DayOfWeek, List<Materia>> materiasPorDia) {
        List<Materia[]> soluciones = new ArrayList<Materia[]>();
        Materia[] materiasRecorridas = new Materia[6];
        obteneMateriasParaLaSemanaWrapped(materiasPorDia, 1, soluciones, materiasRecorridas);
        return soluciones;
    }

    public static List<Materia[]> obteneMateriasParaLaSemanaSinPoda(Map<DayOfWeek, List<Materia>> materiasPorDia) {
        List<Materia[]> soluciones = new ArrayList<Materia[]>();
        Materia[] materiasRecorridas = new Materia[6];
        obteneMateriasParaLaSemanaWrappedSinPoda(materiasPorDia, 1, soluciones, materiasRecorridas);
        return soluciones;
    }
    public static void obteneMateriasParaLaSemanaWrapped(Map<DayOfWeek, List<Materia>> materiasPorDia, int diaActual, List<Materia[]> soluciones, Materia[] materiasRecorridas) {
        if (diaActual == 6) {
            int length = 6;
            Materia[] materiasSolucion = Arrays.copyOf(materiasRecorridas, length);
            soluciones.add(materiasSolucion);
        } else {
            List<Materia> materiasDisponiblesPorDia = obtenerMateriasDisponiblesParaDia(materiasPorDia, diaActual);
            for (Materia materia : materiasDisponiblesPorDia) {
                if (!yaFueUtilizada(materia, materiasRecorridas)){
                    materiasRecorridas[diaActual] = materia;
                    diaActual ++;
                    obteneMateriasParaLaSemanaWrapped(materiasPorDia, diaActual, soluciones, materiasRecorridas);
                    diaActual --;
                    materiasRecorridas[diaActual] = null;
                }
            }
        }
    }

    public static void obteneMateriasParaLaSemanaWrappedSinPoda(Map<DayOfWeek, List<Materia>> materiasPorDia, int diaActual, List<Materia[]> soluciones, Materia[] materiasRecorridas) {
        if (diaActual == 6) {
            if (esSolucionValida(materiasRecorridas)){
                int length = 6;
                Materia[] materiasSolucion = Arrays.copyOf(materiasRecorridas, length);
                soluciones.add(materiasSolucion);
            }

        } else {
            List<Materia> materiasDisponiblesPorDia = obtenerMateriasDisponiblesParaDia(materiasPorDia, diaActual);
            for (Materia materia : materiasDisponiblesPorDia) {
                materiasRecorridas[diaActual] = materia;
                diaActual ++;
                obteneMateriasParaLaSemanaWrappedSinPoda(materiasPorDia, diaActual, soluciones, materiasRecorridas);
                diaActual --;
                materiasRecorridas[diaActual] = null;
            }
        }
    }

    private static boolean esSolucionValida(Materia[] materiasRecorridas) {
        for(int j = 1 ; j < materiasRecorridas.length - 1; j ++){
            for (int i = j + 1; i < materiasRecorridas.length; i++) {
                if (materiasRecorridas[j] == materiasRecorridas[i]){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean yaFueUtilizada(Materia materia, Materia[] materiasRecorridas) {
        for(Materia materiaRecorrida: materiasRecorridas){
            if (materia == materiaRecorrida){
                return true;
            }
        }
        return false;
    }

    public static List<Materia> obtenerMateriasDisponiblesParaDia(Map<DayOfWeek, List<Materia>> materiasPorDia, int diaActual){
        return materiasPorDia.get(DayOfWeek.of(diaActual));
    }
}

class Materia {
    private String ID;
    public Materia(String ID){
        this.ID = ID ;
    }
    public String getID(){
        return this.ID ;
    }
}

