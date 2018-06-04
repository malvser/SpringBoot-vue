<template>
    <el-dialog title="Edit" v-model="dialogFormVisible" :close-on-click-modal="false" :show-close="false">
        <el-form :model="form">
            <el-form-item label="id" :label-width="formLabelWidth">
                <el-input :disabled="true" v-model="form.id" auto-complete="off"></el-input>
            </el-form-item>
<!--
            <el-form-item label="originalid" :label-width="formLabelWidth">
                <el-input :disabled="true" v-model="form.originalid" auto-complete="off"></el-input>
            </el-form-item>
-->
            <el-form-item label="firstName" :label-width="formLabelWidth">
                <el-input v-model="form.firstName" auto-complete="off"></el-input>
            </el-form-item>

            <el-form-item label="secondName" :label-width="formLabelWidth">
                <el-input v-model="form.secondName" auto-complete="off"></el-input>
            </el-form-item>

            <el-form-item label="password" :label-width="formLabelWidth">
              <el-input  v-model="form.password" auto-complete="off"></el-input>
            </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button :plain="true" type="danger" v-on:click="canclemodal">Отменить</el-button>
            <el-button :plain="true" @click="updateForm(form)">Сохранить</el-button>
        </div>
    </el-dialog>
</template>


<script>
    export default {
        data(){
            return {
                formLabelWidth: '120px',
            }
        },
        props: ['dialogFormVisible', 'form'],

        methods: {
            updateForm: function (formName) {
                let Id = formName.id;
                let firstName = formName.firstName;
                let secondName = formName.secondName;
                let password = formName.password;
                this.$axios.put('http://127.0.0.1:8000/api/employee/detail/' + Id, {
                  firstName: firstName,
                  secondName: secondName,
                  password: password
                })
                    .then(function (response) {
                        console.log(response);
                        this.form = response.data;

                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                location.reload();
            },
            canclemodal: function () {
                this.$emit('canclemodal');
            }
        }

    }

</script>
