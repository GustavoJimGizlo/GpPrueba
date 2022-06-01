import { NgModule } from "@angular/core";
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
//decorador-marca el comportamiento de una clase;
@NgModule({
    exports:[MatToolbarModule, 
        MatCardModule,
         MatButtonModule, 
         MatIconModule]
})

//clase
export class MaterialModule{}
