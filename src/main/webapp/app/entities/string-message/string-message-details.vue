<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stringMessage">
                <h2 class="jh-entity-heading"><span>StringMessage</span> {{stringMessage.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Message</span>
                    </dt>
                    <dd>
                        <span>{{stringMessage.message}}</span>
                    </dd>
                    <dt>
                        <span>Received At</span>
                    </dt>
                    <dd>
                        <span>{{stringMessage.receivedAt | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./string-message-details.component.ts">
</script>
