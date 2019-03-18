package io.aeron.agent;

import io.aeron.cluster.Election;
import org.agrona.MutableDirectBuffer;

final class ClusterEventEncoder
{
    static int encodeElectionStateChange(
        final MutableDirectBuffer encodedBuffer,
        final Election.State newState,
        final long nowMs)
    {
        final int timestampOffset = encodedBuffer.putStringAscii(0, newState.name());
        encodedBuffer.putLong(timestampOffset, nowMs);
        return timestampOffset + Long.BYTES;
    }

    static int newLeadershipTerm(
        final MutableDirectBuffer encodedBuffer,
        final long logLeadershipTermId,
        final long logPosition,
        final long leadershipTermId,
        final long maxLogPosition,
        final int leaderMemberId,
        final int logSessionId)
    {
        int offset = 0;
        encodedBuffer.putLong(0, logLeadershipTermId);
        offset += Long.BYTES;
        encodedBuffer.putLong(offset, logPosition);
        offset += Long.BYTES;
        encodedBuffer.putLong(offset, leadershipTermId);
        offset += Long.BYTES;
        encodedBuffer.putLong(offset, maxLogPosition);
        offset += Long.BYTES;
        encodedBuffer.putInt(offset, leaderMemberId);
        offset += Integer.BYTES;
        encodedBuffer.putInt(offset, logSessionId);
        return offset + Integer.BYTES;
    }
}
