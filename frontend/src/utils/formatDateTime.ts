export const formatDateTime = (date: Date): string => {
   const day = date.getDate()
   const month = date.toLocaleString('ru-RU', { month: 'long' })
   const year = date.getFullYear()
   const hours = date.getHours().toString().padStart(2, '0')
   const minutes = date.getMinutes().toString().padStart(2, '0')

   return `${day} ${month} ${year}, ${hours}:${minutes}`
}
