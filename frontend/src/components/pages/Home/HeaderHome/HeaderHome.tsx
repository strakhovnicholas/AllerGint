import { useEffect, useState } from 'react'
import style from './HeaderHome.module.css'
import { getWeatherDescription } from '../../../../utils/weatherCodes'

interface WeatherCard {
    town: string
    temp: number
    weather: string
    pollen: string
    source: string[]
}

interface User {
    name: string
}

function HeaderHome() {

    const [card, setCard] = useState<WeatherCard | null>(null)
    const [user, setUser] = useState<User | null>(null)

    useEffect(() => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'

        fetch(`http://localhost:8080/api/users/${userId}`)
            .then(res => res.json())
            .then((data: User) => setUser(data))
            .catch(err => console.error('Ошибка при получении юзера:', err))

        navigator.geolocation.getCurrentPosition(
            position => {
                const lat = position.coords.latitude
                const lon = position.coords.longitude

                fetch(
                    `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current_weather=true&timezone=auto`
                )
                    .then(res => res.json())
                    .then(data => {
                        setCard({
                            town: data.timezone.split('/')[1],
                            temp: data.current_weather.temperature,
                            weather: getWeatherDescription(data.current_weather.weathercode),
                            pollen: 'Низкий уровень пыльцы',
                            source: ['Берёза', 'Дуб'],
                        })
                    })
                    .catch(err => console.error('Ошибка при получении погоды:', err))
            },
            err => console.error('Ошибка геолокации:', err)
        )
    }, [])

    return (
        <header className={style.header}>
            <div className={style.headerTitle}>
                <div className={style.headerTitleText}>
                    <h1 className={style.headerTitleTitle}>
                        Добро пожаловать, {user?.name ?? 'User'}
                    </h1>
                    <h6 className={style.headerTitleDescription}>Сегодня хороший день</h6>
                </div>
                <div className={style.headerTitleIcon}>

                </div>
            </div>

            <div className={style.headerWeatherCard}>
                <div className={style.weatherCardLeft}>
                    <div className={style.leftTown}>{card?.town}</div>
                    <div className={style.leftTemperature}>{card?.temp}°C</div>
                    <div className={style.leftWeather}>{card?.weather}</div>
                </div>
                <div className={style.weatherCardRight}>
                    <div className={style.rightWeatherIcon}>
                    </div>
                    <div className={style.rightPollen}>{card?.pollen}</div>
                    <div className={style.rightSource}>
                        {card?.source?.join(', ') ?? ''}
                    </div>
                </div>
            </div>
        </header>
    )
}

export default HeaderHome