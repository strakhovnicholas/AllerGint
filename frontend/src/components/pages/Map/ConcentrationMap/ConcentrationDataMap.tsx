import { useState, useEffect } from 'react'
import { CircleMarker, Tooltip } from 'react-leaflet'
import style from './ConcentrationDataMap.module.css'

interface ConcentrationData {
   id: string
   lat: number
   lng: number
   concentration: number
   timestamp: string
}

function ConcentrationDataMap() {
   const [concentrationData, setConcentrationData] = useState<
      ConcentrationData[]
   >([])
   const [loading, setLoading] = useState(true)

   useEffect(() => {
      // Имитация получения данных с сервера
      const fetchConcentrationData = async () => {
         // В реальном приложении здесь будет fetch к API
         const mockData: ConcentrationData[] = [
            {
               id: '1',
               lat: 55.7558,
               lng: 37.6176,
               concentration: 85,
               timestamp: '2024-01-15T10:30:00Z',
            },
            {
               id: '2',
               lat: 55.7458,
               lng: 37.6276,
               concentration: 42,
               timestamp: '2024-01-15T10:30:00Z',
            },
            {
               id: '3',
               lat: 55.7658,
               lng: 37.6076,
               concentration: 15,
               timestamp: '2024-01-15T10:30:00Z',
            },
            {
               id: '4',
               lat: 55.7358,
               lng: 37.6376,
               concentration: 120,
               timestamp: '2024-01-15T10:30:00Z',
            },
            {
               id: '5',
               lat: 55.7758,
               lng: 37.5976,
               concentration: 67,
               timestamp: '2024-01-15T10:30:00Z',
            },
         ]

         // Симуляция задержки загрузки
         setTimeout(() => {
            setConcentrationData(mockData)
            setLoading(false)
         }, 1000)
      }

      fetchConcentrationData()
   }, [])

   // Функция для определения цвета на основе концентрации
   const getColor = (concentration: number): string => {
      if (concentration < 20) return '#22c55e' // зеленый
      if (concentration < 50) return '#eab308' // желтый
      if (concentration < 100) return '#f97316' // оранжевый
      return '#ef4444' // красный
   }

   // Функция для определения прозрачности на основе концентрации
   const getOpacity = (concentration: number): number => {
      if (concentration < 20) return 0.4
      if (concentration < 50) return 0.6
      if (concentration < 100) return 0.8
      return 0.9
   }

   // Функция для определения размера на основе концентрации
   const getRadius = (concentration: number): number => {
      const baseRadius = 15
      const maxRadius = 40
      const normalized = Math.min(concentration / 150, 1) // нормализуем к 0-1
      return baseRadius + (maxRadius - baseRadius) * normalized
   }

   if (loading) {
      return (
         <div className={style.loading}>
            <div className={style.spinner}></div>
            <p>Загрузка данных о концентрации пыльцы...</p>
         </div>
      )
   }

   return (
      <div className={style.container}>
         {concentrationData.map((point) => {
            const color = getColor(point.concentration)
            const opacity = getOpacity(point.concentration)
            const radius = getRadius(point.concentration)
            const outerRadius = radius * 2

            return (
               <div key={point.id}>
                  {/* Внешний размытый круг */}
                  <CircleMarker
                     center={[point.lat, point.lng]}
                     radius={outerRadius}
                     pathOptions={{
                        color: color,
                        fillColor: color,
                        fillOpacity: opacity * 0.2,
                        weight: 0,
                        opacity: opacity * 0.3,
                     }}
                     className={style.outerMarker}
                  >
                     <Tooltip>
                        <div className={style.tooltip}>
                           <div className={style.tooltipHeader}>
                              <div
                                 className={style.colorIndicator}
                                 style={{ backgroundColor: color }}
                              ></div>
                              <span>Концентрация пыльцы</span>
                           </div>
                           <div className={style.tooltipContent}>
                              <div className={style.concentrationValue}>
                                 {point.concentration} зерен/м³
                              </div>
                              <div className={style.timestamp}>
                                 {new Date(point.timestamp).toLocaleString(
                                    'ru-RU'
                                 )}
                              </div>
                              <div className={style.coordinates}>
                                 {point.lat.toFixed(4)}, {point.lng.toFixed(4)}
                              </div>
                           </div>
                        </div>
                     </Tooltip>
                  </CircleMarker>

                  {/* Внутренний четкий круг */}
                  <CircleMarker
                     center={[point.lat, point.lng]}
                     radius={radius}
                     pathOptions={{
                        color: color,
                        fillColor: color,
                        fillOpacity: opacity,
                        weight: 2,
                        opacity: 1,
                     }}
                     className={style.innerMarker}
                  />
               </div>
            )
         })}
      </div>
   )
}

export default ConcentrationDataMap
