import { FormControl, ValidationErrors } from "@angular/forms";

export class EcommerceValidador {

    // Validación para verificar si el valor no contiene solo espacion en blanco 
    static noVacioConEspacios(control: FormControl) : ValidationErrors | null{

        //Verifica si el valor del control no es nulo y si el valor contiene solo espacion en blanco
        if((control.value != null) && (control.value.trim().length === 0)){

        // Si contiene solo espacion en blanco, es inválido, por lo que se retorna un objeto de error
            return {'noVacioConEspacios': true};
        }

        else{
            // Si el valor contiene caracteres distintos a los espacios en blanco, es valido
             return null;
        }

       
        
    }
}
