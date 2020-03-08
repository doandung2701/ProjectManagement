import { Component } from '@angular/core';
import { Router,Event, NavigationStart,NavigationError,NavigationEnd,NavigationCancel } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  loading=false;
  constructor(private router:Router){
    this.router.events.subscribe((event:Event)=>{
      switch (true) {
        case event instanceof NavigationStart:
          this.loading=true;
          break;
        case event instanceof NavigationEnd:
        case event instanceof NavigationCancel:
        case event instanceof NavigationError:
          this.loading=false;
          break;
        default:
          break;
      }
    });
  }
}
