import { SummaryTaskAndStatus } from '../SummaryTaskAndStatus.model';
import { SummaryTaskAndCategory } from '../SummaryTaskAndCategory.model';

export interface DashboardDto{
    taskByStatus:SummaryTaskAndStatus[];
    taskByCategory:SummaryTaskAndCategory[];
}