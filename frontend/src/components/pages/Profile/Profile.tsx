import DataProfile from './DataProfile/DataProfile'
import HeaderProfile from './HeaderProfile/HeaderProfile'
import MedProfile from './MedProfile/MedProfile'
import style from './Profile.module.css'
import TherapyProfile from './TherapyProfile/TherapyProfile'

function Profile() {
   return (
      <div>
         <HeaderProfile />

         <div className={style.data}>
            <DataProfile />
         </div>
         <div className={style.med}>
            <MedProfile />
         </div>
         <div className={style.therapy}>
            <TherapyProfile />
         </div>
      </div>
   )
}

export default Profile
