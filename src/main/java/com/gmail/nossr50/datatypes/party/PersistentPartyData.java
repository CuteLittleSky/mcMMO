package com.gmail.nossr50.datatypes.party;

import com.neetgames.neetlib.dirtydata.DirtyData;
import com.neetgames.neetlib.dirtydata.DirtySet;
import com.neetgames.neetlib.mutableprimitives.MutableBoolean;
import com.neetgames.neetlib.mutableprimitives.MutableString;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PersistentPartyData {

    private final @NotNull MutableBoolean dirtyFlag; //Dirty values in this class will change this flag as needed
    private final @NotNull DirtyData<MutableString> partyName;
    private final @NotNull DirtySet<PartyMember> partyMembers; //TODO: Add cache for subsets

    public PersistentPartyData(@NotNull String partyName, @NotNull Set<PartyMember> partyMembers) throws RuntimeException {
        dirtyFlag = new MutableBoolean(false);
        this.partyName = new DirtyData<>(new MutableString(partyName), dirtyFlag);
        this.partyMembers = new DirtySet<>(new HashSet<>(partyMembers), dirtyFlag);
    }

    public @NotNull String getPartyName() {
        return partyName.getData().getImmutableCopy();
    }

    public @NotNull Set<PartyMember> getPartyMembers() {
        return partyMembers;
    }

    public boolean isDataDirty() {
        return dirtyFlag.getImmutableCopy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentPartyData that = (PersistentPartyData) o;
        return dirtyFlag.equals(that.dirtyFlag) && partyName.equals(that.partyName) && partyMembers.equals(that.partyMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dirtyFlag, partyName, partyMembers);
    }
}
