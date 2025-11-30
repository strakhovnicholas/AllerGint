import DiaryNotebook from './DiaryNotebook/DiaryNotebook'
import AiRecommendationsNotebook from './AiRecommendationsNotebook/AiRecommendationsNotebook'
import HealthNotebook from './HealthNotebook/HealthNotebook'
import SymptomNotebook from './SymptomsNotebook/SymptomNotebook'
import PillsNotebook from './PillsNotebook/PillsNotebook'
import NotesNotebook from './NotesNotebook/NotesNotebook'
import WheatherNotebook from './WheatherNotebook/WheatherNotebook'

function Notebook() {
  return (
    <div>
      <DiaryNotebook />
      <AiRecommendationsNotebook />
      <HealthNotebook></HealthNotebook>
      <SymptomNotebook></SymptomNotebook>
      <PillsNotebook></PillsNotebook>
      <WheatherNotebook></WheatherNotebook>
      <NotesNotebook></NotesNotebook>
    </div>
  )
}

export default Notebook
