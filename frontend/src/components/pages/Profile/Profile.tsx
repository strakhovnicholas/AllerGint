import DataProfile from './DataProfile/DataProfile'
import HeaderProfile from './HeaderProfile/HeaderProfile'
import MedProfile from './MedProfile/MedProfile'
import style from './Profile.module.css'
import TherapyProfile from './TherapyProfile/TherapyProfile'

function Profile() {
   return (
      <div>
         <HeaderProfile />

         <div className={style.med}>
            <MedProfile />
         </div>
      </div>
   )
}

export default Profile
