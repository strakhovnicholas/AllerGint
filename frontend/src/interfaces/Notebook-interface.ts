export interface Day {
  id: string
  userId: string
  healthState: string
  userNotes: string
  medicines: [
    {
      name: string
      dosage: string
    }
  ]
  userSymptoms: [
    {
      symptom: string
      severity: string
    }
  ]
  weathers: [
    {
      temperature: 0
      condition: string
    }
  ]
}
