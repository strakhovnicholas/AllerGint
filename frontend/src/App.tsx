import './App.css'
import Header from './components/UI/Header/Header'
import Home from './components/pages/Home/Home'
import Notebook from './components/pages/NoteBook/NoteBook'
import Map from './components/pages/Map/Map'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Profile from './components/pages/Profile/Profile'

function App() {
   return (
      <BrowserRouter>
         <div className="appContainer">
            <Header />
            <Routes>
               <Route index element={<Home />} />
               <Route path="notebook" element={<Notebook />} />
               <Route path="map" element={<Map />} />
               <Route path="profile" element={<Profile />} />
            </Routes>

            <div className="footer"></div>
         </div>
      </BrowserRouter>
   )
}

export default App
