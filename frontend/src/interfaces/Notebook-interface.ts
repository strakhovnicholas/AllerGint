export interface MedicineDto {
    id: string
    medicineName: string
    medicineDescription: string
    medicineType: MedicineType
    dose: number
    doseMeasureType: DoseMeasureType
}

export enum MedicineType {
    PILLS = 'PILLS',
    SYRUP = 'SYRUP',
    SPRAY = 'SPRAY'
}

export enum DoseMeasureType {
    MG = 'MG',
    ML = 'ML',
    G = 'G'
}

export interface UserSymptomDto {
    id: string
    symptomName: string
    timestamp: string
    symptomState: string
}

export interface WeatherDto {
    id: string
    weatherCondition: string
    timestamp: string
    temperature: number
}

export interface Day {
    id: string
    userId: string
    healthState: string
    timestamp: string
    medicines: MedicineDto[]
    userSymptoms: UserSymptomDto[]
    weathers: WeatherDto[]
    userNotes: string
    aiNotes: string
}

export interface UserSymptom {
    id: string
    symptomName: string
    symptomState: 'STRONG' | 'MEDIUM' | 'WEAK' | null
    timestamp?: string
}
