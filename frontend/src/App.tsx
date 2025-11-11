import './App.css'
import Footer from './components/UI/Footer/Footer'
import Home from './components/pages/Home/Home'
import Notebook from './components/pages/NoteBook/NoteBook'
import Map from './components/pages/Map/Map'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {
   return (
      <BrowserRouter>
         <div>
            <Routes>
               <Route index element={<Home />} />
               <Route path="notebook" element={<Notebook />} />
               <Route path="map" element={<Map />} />
            </Routes>

            <div className="footer"></div>
            <Footer />
         </div>
      </BrowserRouter>
   )
}

export default App
