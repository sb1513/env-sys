import {ref} from "vue";
import {defineStore} from "pinia";

const useSupAqiFeedbackStore = defineStore('supAqiFeedback',()=>{
    const aqi = ref({
        telId: '',
        provinceId: '',
        cityId: '',
        address: '',
        information: '',
        estimatedGrade: '',
        state: 0,
    })
    function $reset(){
        aqi.value={
            telId: '',
            provinceId: '',
            cityId: '',
            address: '',
            information: '',
            estimatedGrade: '',
            state: 0,
        }
    }
    return {aqi,$reset}
})

export default useSupAqiFeedbackStore