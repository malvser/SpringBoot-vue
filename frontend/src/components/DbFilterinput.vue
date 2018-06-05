<template>
    <el-form :inline="true" :model="formInline">

        <el-form-item label="Имя">
            <el-select v-model="formInline.firstName" clearable placeholder="выберите имя"
                       v-on:visible-change="selectDemo">
                <el-option
                        v-for="(item, index) in type_options"
                        :key = "index"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item v-if='formInline.firstName' label="Описание">
            <el-input v-model="formInline.secondName" placeholder="Первые буквы Фамилии"></el-input>
        </el-form-item>

        <el-form-item v-else='formInline.firstName' label="Описание">
            <el-input v-model="formInline.secondName" disabled placeholder="Первые буквы Фамилии"></el-input>
        </el-form-item>

    </el-form>
</template>

<script>
    import lodash from 'lodash'
    import Bus from '../eventBus'

    export default {
        name: 'db-filterinput',
        data() {
            return {
                type_options: [],
                formInline: {
                    firstName: '',
                    secondName: ''
                },
                formLabelWidth: '120px'
            }
        },

        watch: {
            'formInline.firstName': 'filterResultData',
            'formInline.secondName': 'filterResultData'
        },

        methods: {
            selectDemo: function (params) {
                if (params) {
                    this.$axios.get("http://127.0.0.1:8000/api/employee/firstName")
                        .then((response) => {
                            this.type_options = response.data;
                            console.log(response.data);
                        }).catch(function (response) {
                        console.log(response)
                    });
                }

            },
            filterResultData: _.debounce(
                function () {
                    this.$axios.get("http://127.0.0.1:8000/api/employee", {
                        params: {
                            firstName: this.formInline.firstName,
                            secondName: this.formInline.secondName,
                        }
                    }).then((response) => {
                        response.data['firstName'] = this.formInline.firstName;
                        response.data['secondName'] = this.formInline.secondName;
                        Bus.$emit('filterResultData', response.data);
                        console.log(response.data);
                    }).catch(function (response) {
                        console.log(response)
                    });

                },
                500
            ),
        }
    }


</script>
