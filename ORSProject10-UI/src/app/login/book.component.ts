import { Component, OnInit } from "@angular/core";


@Component({
    selector:'book-component',
    templateUrl: './book.component.html',
    styleUrls:['./book.component.css'],
})

export class BookComponent implements OnInit{
    constructor() {
        console.log('BookComponent constructor called');
     }
   ngOnInit() {
    console.log('BookComponent  ngOnInit called');
   }
}