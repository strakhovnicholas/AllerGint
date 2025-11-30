import ConcentrationMap from './ConcentrationMap/ConcentrationMap'
import FilterMap from './FilterMap/FilterMap'
import LocationMap from './LocationMap/LocationMap'
import style from './Map.module.css'
import MapMap from './MapMap/MapMap'
import NearPlacesMap from './NearPlacesMap/NearPlacesMap'

function Map() {
   return (
      <>
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
         <div className={style.nearPlaces}>
            <NearPlacesMap />
         </div>
      </>
   )
}

export default Map
