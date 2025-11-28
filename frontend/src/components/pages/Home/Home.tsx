import ActionsHome from './ActionsHome/ActionsHome'
import HeaderHome from './HeaderHome/HeaderHome'
import style from './Home.module.css'
import RecomendationsHome from './RecomendationsHome/RecomendationsHome'

interface HomeProps {
    userId: string
    diaryPageId: string
}

function Home() {
    return (<div className={style.homeContainer}>
        <HeaderHome/>
        <div className={style.recomendations}>
            <RecomendationsHome/>
        </div>
        <div className={style.actions}>
            <ActionsHome/>
        </div>
    </div>)
}

export default Home
