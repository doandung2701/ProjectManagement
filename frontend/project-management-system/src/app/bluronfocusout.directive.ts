import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appBluronfocusout]'
})
export class BluronfocusoutDirective {
  private el: HTMLElement;
  constructor(
    private elementRef: ElementRef,
    // private currencyPipe: MyCurrencyPipe 
  ) {
    this.el = this.elementRef.nativeElement;
  }
  @HostListener("focus", ["$event.target.value"])
  onBlur(value) {
    debugger;
    //this.el.value = this.currencyPipe.transform(value); 
  }
}
