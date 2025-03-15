package com.lawfirm.common.log.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GenericOperationEvent {
    private final String entityType;
    private final Long entityId;
    private final Long operatorId;
    private final LocalDateTime operationTime;
    
    public static class Create extends GenericOperationEvent {
        public Create(String entityType, Long entityId, Long operatorId) {
            super(entityType, entityId, operatorId, LocalDateTime.now());
        }
    }

    public static class Update extends GenericOperationEvent {
        private final String previousState;
        private final String newState;

        public Update(String entityType, Long entityId, Long operatorId, String previousState, String newState) {
            super(entityType, entityId, operatorId, LocalDateTime.now());
            this.previousState = previousState;
            this.newState = newState;
        }
    }

    public static class Delete extends GenericOperationEvent {
        private final String deleteReason;

        public Delete(String entityType, Long entityId, Long operatorId, String deleteReason) {
            super(entityType, entityId, operatorId, LocalDateTime.now());
            this.deleteReason = deleteReason;
        }
    }
}
