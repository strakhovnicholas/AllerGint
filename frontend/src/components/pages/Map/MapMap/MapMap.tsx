import { useState } from 'react'
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import style from './MapMap.module.css'
import ConcentrationDataMap from '../ConcentrationMap/ConcentrationDataMap'

// Настройка иконок для маркеров
delete (L.Icon.Default.prototype as any)._getIconUrl
L.Icon.Default.mergeOptions({
   iconRetinaUrl:
      'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
   iconUrl:
      'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
   shadowUrl:
      'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
})

function MapMap() {
   const [zoom, setZoom] = useState(10)
   const [center, setCenter] = useState<[number, number]>([55.7558, 37.6176]) // Москва по умолчанию

   const handleZoomIn = () => {
      if (zoom < 18) {
         setZoom((prev) => prev + 1)
      }
   }

   const handleZoomOut = () => {
      if (zoom > 1) {
         setZoom((prev) => prev - 1)
      }
   }

   return (
      <div className={style.mapContainer}>
         <MapContainer
            center={center}
            zoom={zoom}
            style={{ height: '100%', width: '100%' }}
            zoomControl={false}
            scrollWheelZoom={true}
            touchZoom={true}
            doubleClickZoom={true}
            key={zoom}
         >
            <TileLayer
               attribution='© <a href="https://carto.com/attributions">CARTO</a>'
               url="https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"
            />
            <Marker position={center}>
               <Popup>
                  Центр карты
                  <br />
                  Москва, Россия
               </Popup>
            </Marker>

            {/* Компонент отображения концентрации пыльцы */}
            <ConcentrationDataMap />
         </MapContainer>

         <div className={style.controls}>
            <button
               className={style.plusBtn}
               onClick={handleZoomIn}
               disabled={zoom >= 18}
            >
               <div className={style.vertical}></div>
               <div className={style.horizontal}></div>
            </button>

            <button
               className={style.minusBtn}
               onClick={handleZoomOut}
               disabled={zoom <= 1}
            >
               <div className={style.horizontal}></div>
            </button>
         </div>

         <div className={style.zoomLevel}>Масштаб: {zoom}</div>
      </div>
   )
}

export default MapMap
