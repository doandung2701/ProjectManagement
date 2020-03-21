import { Component, OnInit, HostListener, Directive, ViewChildren, ElementRef } from '@angular/core';

@Component({
    selector: 'app-page-not-found',
    templateUrl: './page-not-found.component.html',
    styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {
    @ViewChildren("box_ghost") div: any;
    pageX: number;
    pageY: number;
    mouseY: number = 0;
    mouseX: number = 0;
    constructor() {
        this.pageY = document.body.clientHeight;
        this.pageX = document.body.clientWidth;
    }
    ngOnInit(): void {
    }
    @HostListener('window:mousemove', ['$event'])
    handlerMouseMouse(event: MouseEvent) {
        this.mouseY = event.pageY;
        var yAxis = (this.pageY / 2 - this.mouseY) / this.pageY * 300;
        //horizontalAxis
        this.mouseX = event.pageX / -this.pageX;
        var xAxis = -this.mouseX * 100 - 100;
        this.div.first.nativeElement.style.transform ='translate('+ xAxis + '%,-' + yAxis + '%)';
    }
}
