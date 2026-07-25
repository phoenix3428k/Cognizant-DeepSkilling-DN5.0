import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'creditLabel',
  standalone: true
})
export class CreditLabelPipe implements PipeTransform {
  transform(value: number | null): string {
    if (value === null || value === 0) {
      return 'No Credits';
    }
    return value === 1 ? '1 Credit' : `${value} Credits`;
  }
}
