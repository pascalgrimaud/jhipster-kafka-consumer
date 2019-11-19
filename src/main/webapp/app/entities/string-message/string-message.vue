<template>
    <div>
        <h2 id="page-heading">
            <span id="string-message-heading">String Messages</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && stringMessages && stringMessages.length === 0">
            <span>No stringMessages found</span>
        </div>
        <div class="table-responsive" v-if="stringMessages && stringMessages.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span>ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('message')"><span>Message</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('receivedAt')"><span>Received At</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stringMessage in stringMessages"
                    :key="stringMessage.id">
                    <td>
                        <router-link :to="{name: 'StringMessageView', params: {stringMessageId: stringMessage.id}}">{{stringMessage.id}}</router-link>
                    </td>
                    <td>{{stringMessage.message}}</td>
                    <td>{{stringMessage.receivedAt | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'StringMessageView', params: {stringMessageId: stringMessage.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="consumerApp.stringMessage.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stringMessage-heading" >Are you sure you want to delete this String Message?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stringMessage" v-on:click="removeStringMessage()">Delete</button>
            </div>
        </b-modal>
        <div v-show="stringMessages && stringMessages.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./string-message.component.ts">
</script>
