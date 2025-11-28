import './App.css'
import Footer from './components/UI/Footer/Footer'
import Home from './components/pages/Home/Home'
import Notebook from './components/pages/NoteBook/NoteBook'
import Map from './components/pages/Map/Map'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Profile from './components/pages/Profile/Profile'
import Asit from './components/pages/asit/Asit'

function App() {
   return (
      <BrowserRouter>
         <div>
            <Routes>
               <Route index element={<Home />} />
               <Route path="notebook" element={<Notebook />} />
               <Route path="map" element={<Map />} />
               <Route path="profile" element={<Profile />} />
            </Routes>

            <div className="footer"></div>
            <Footer />
         </div>
      </BrowserRouter>
   )
}

export default App
