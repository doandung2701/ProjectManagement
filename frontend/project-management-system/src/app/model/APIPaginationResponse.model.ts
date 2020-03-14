import { UserDto } from './userDto.model';
import { PageResponse } from './pageResponse.model';

export class APIPaginationResponse<T> {
    constructor(
        public content: T[],
        public page: PageResponse
    ) { }
}