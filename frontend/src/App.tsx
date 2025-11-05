import './App.css'
import Footer from './components/UI/Footer/Footer'
import Notebook from './components/pages/NoteBook/NoteBook'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {
  return (
    <BrowserRouter>
      <div>
        <Routes>
          {/* <Route index element={} /> */}
          <Route path="notebook" element={<Notebook />} />
        </Routes>

        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App
