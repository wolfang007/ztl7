import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Myztl7SharedModule } from 'app/shared/shared.module';
import { Myztl7CoreModule } from 'app/core/core.module';
import { Myztl7AppRoutingModule } from './app-routing.module';
import { Myztl7HomeModule } from './home/home.module';
import { Myztl7EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Myztl7SharedModule,
    Myztl7CoreModule,
    Myztl7HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Myztl7EntityModule,
    Myztl7AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class Myztl7AppModule {}
