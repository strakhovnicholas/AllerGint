import { useEffect, useState } from 'react'
import { MapContainer, TileLayer, Marker, Popup, Circle } from 'react-leaflet'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import style from './MapMap.module.css'
import ConcentrationDataMap from '../ConcentrationMap/ConcentrationDataMap'
import { getAddressFromCoords } from '../../../../utils/getAdressFromCoords'

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

interface adress {
   country: string
   city: string
   fullAddress: string
}

function MapMap() {
   const [zoom, setZoom] = useState(10)
   const [center, setCenter] = useState<[number, number]>([55.7558, 37.6176])

   const [adress, setAdress] = useState<adress | null>(null)

   useEffect(() => {
      navigator.geolocation.getCurrentPosition(
         async (position) => {
            setCenter([position.coords.latitude, position.coords.longitude])
            setAdress(
               await getAddressFromCoords(
                  position.coords.latitude,
                  position.coords.longitude
               )
            )
         },
         (error) => {
            console.error('Error getting location:', error)
         }
      )
   }, [])

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

            <Circle
               center={center}
               radius={1000}
               pathOptions={{
                  color: '#2563eb',
                  fillColor: '#3b82f6',
                  fillOpacity: 0.2,
                  weight: 0.7,
               }}
            />

            <Marker position={center}>
               <Popup>
                  Ваше местоположение
                  <br />
                  {adress
                     ? `${adress.country}, ${adress.city}`
                     : 'Геокоординаты недоступны'}
               </Popup>
            </Marker>
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

         <div className={style.attributionCover}></div>
      </div>
   )
}

export default MapMap
