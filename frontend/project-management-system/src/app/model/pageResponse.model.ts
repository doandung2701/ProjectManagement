export class PageResponse {
    constructor(
        public size: number,
        public totalElements: number,
        public  totalPages: number,
        public number: number,
    ) { }
}