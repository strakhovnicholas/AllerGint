export const getAddressFromCoords = async (lat: number, lon: number) => {
   try {
      const response = await fetch(
         `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}&addressdetails=1`
      )
      const data = await response.json()

      return {
         country: data.address.country,
         city: data.address.city || data.address.town || data.address.village,
         fullAddress: data.display_name,
      }
   } catch (error) {
      console.error('Geocoding error:', error)
      return null
   }
}
