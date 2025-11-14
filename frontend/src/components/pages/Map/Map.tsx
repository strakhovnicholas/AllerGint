import ConcentrationMap from './ConcentrationMap/ConcentrationMap'
import FilterMap from './FilterMap/FilterMap'
import HeaderMap from './HeaderMap/HeaderMap'
import LocationMap from './LocationMap/LocationMap'
import style from './Map.module.css'
import MapMap from './MapMap/MapMap'

function Map() {
   return (
      <>
         <HeaderMap />
         <div className={style.filter}>
            <FilterMap />
         </div>
         <div className={style.map}>
            <MapMap />
         </div>
         <div className={style.concentration}>
            <ConcentrationMap />
         </div>
         <div className={style.location}>
            <LocationMap />
         </div>
      </>
   )
}

export default Map
